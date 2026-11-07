package edu.cwi;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Main {
    public static void main(String[] args) {
        Javalin.create(config -> {
            config.fileRenderer(new JavalinJte());
            config.staticFiles.add("/public", Location.CLASSPATH);

            config.routes.beforeMatched(ctx -> {
                if (ctx.routeRoles().isEmpty()) {
                    return;
                }

                String role = ctx.cookieStore().get("user-role");

                if (role == null) {
                    throw new UnauthorizedResponse();
                }

                AppRole appRole = AppRole.valueOf(role);

                if (!ctx.routeRoles().contains(appRole)) {
                    throw new UnauthorizedResponse();
                }
            });

            config.routes.post("/account", AuthController::authenticate);
            config.routes.get("/logout", AuthController::logout);

            config.routes.apiBuilder(() -> {
                path("/account", List.of(AppRole.CUSTOMER), () -> {
                    get(ctx -> {
                        String id = ctx.cookieStore().get("user-id");
                        Optional<UserAccount> account = AuthController.getUser(id);

                        if (account.isEmpty()) {
                            throw new UnauthorizedResponse();
                        }

                        ctx.render("jte/accountPage.jte", Map.of(
                            "title", "Your Account",
                            "account", account.get()
                        ));
                    });
                });

                path("/transactions", () -> {
                    TransactionsController controller = new TransactionsController();

                    crud("{id}", controller);
                    post("/update", ctx -> controller.updateForm(ctx));
                    post("/delete", ctx -> controller.deleteForm(ctx));
                });
            });
        }).start(7070);
    }
}