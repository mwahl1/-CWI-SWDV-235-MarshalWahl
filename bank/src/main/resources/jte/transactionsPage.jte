@import java.util.Map

@param Map<String, String> transactions

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Transactions</title>
  </head>
  <body>
    <h1>Transactions</h1>
    <ul>
      @for(var entry : transactions.entrySet())
        <li>${entry.getKey()}: ${entry.getValue()}</li>
      @endfor
    </ul>

    <h2>Create Transaction</h2>
    <form method="post" action="/transactions">
      <input type="text" name="transaction" placeholder="Transaction">
      <button type="submit">Create</button>
    </form>

    <h2>Update Transaction</h2>
    <form method="post" action="/transactions/update">
      <input type="text" name="id" placeholder="Transaction ID">
      <input type="text" name="transaction" placeholder="New value">
      <button type="submit">Update</button>
    </form>

    <h2>Delete Transaction</h2>
    <form method="post" action="/transactions/delete">
      <input type="text" name="id" placeholder="Transaction ID">
      <button type="submit">Delete</button>
    </form>
  </body>
</html>
