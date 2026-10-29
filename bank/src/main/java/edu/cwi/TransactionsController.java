
package edu.cwi;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import static j2html.TagCreator.*;

public class TransactionsController implements CrudHandler {

    private final Map<String, String> transactions = new ConcurrentHashMap<>();

    @Override
    public void getAll(Context ctx) {
        ctx.html(
            html(
                body(
                    h1("All Transactions")
                )
            ).render()
        );
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
        String body = ctx.body();
        String id = String.valueOf(transactions.size() + 1);

        transactions.put(id, body);

        getAll(ctx);
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
}
