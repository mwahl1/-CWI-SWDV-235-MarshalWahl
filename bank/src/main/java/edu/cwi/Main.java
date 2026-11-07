
package edu.cwi;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

public class Main {
    public static void main(String[] args) {
        Javalin.create(config -> {
            config.fileRenderer(new JavalinJte());
            config.staticFiles.add("/public", Location.CLASSPATH);

            config.routes.post("/account", ctx -> {
                ctx.redirect("/account.html");
            });

            config.routes.get("/logout", ctx -> {
                ctx.redirect("/");
            });

            config.routes.apiBuilder(() -> {
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
