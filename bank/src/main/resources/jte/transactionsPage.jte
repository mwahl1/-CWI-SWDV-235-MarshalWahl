
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
  </body>
</html>
