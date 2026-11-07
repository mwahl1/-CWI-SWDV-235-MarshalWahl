
package edu.cwi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import static j2html.TagCreator.body;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.html;
import static j2html.TagCreator.p;

public class TransactionsController implements CrudHandler {

    private final Map<String, String> transactions = new ConcurrentHashMap<>();

    @Override
    public void getAll(Context ctx) {
        UserAccount account = new UserAccount(
            "Test User",
            1250.50,
            transactions
        );

        ctx.render("jte/accountPage.jte", Map.of(
            "title", "Account Overview",
            "account", account
        ));
    }

    @Override
    public void getOne(Context ctx, String resourceId) {
        String transaction = transactions.get(resourceId);

        if (transaction != null) {
            ctx.html(
                html(
                    body(
                        h1("Transaction"),
                        p(transaction)
                    )
                ).render()
            );
        } else {
            ctx.status(404).result("Transaction not found");
        }
    }

    @Override
    public void create(Context ctx) {
        String transaction = ctx.formParam("transaction");
        String id = String.valueOf(transactions.size() + 1);

        transactions.put(id, transaction);
        ctx.redirect("/transactions");
    }

    @Override
    public void update(Context ctx, String resourceId) {
        if (transactions.containsKey(resourceId)) {
            transactions.put(resourceId, ctx.body());
            getAll(ctx);
        } else {
            ctx.status(404).result("Transaction not found");
        }
    }

    @Override
    public void delete(Context ctx, String resourceId) {
        transactions.remove(resourceId);
        getAll(ctx);
    }

    public void updateForm(Context ctx) {
        String id = ctx.formParam("id");
        String transaction = ctx.formParam("transaction");

        if (transactions.containsKey(id)) {
            transactions.put(id, transaction);
            ctx.redirect("/transactions");
        } else {
            ctx.status(404).result("Transaction not found");
        }
    }

    public void deleteForm(Context ctx) {
        String id = ctx.formParam("id");

        transactions.remove(id);
        ctx.redirect("/transactions");
    }
}
