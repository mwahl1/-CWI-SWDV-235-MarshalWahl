
@param String title
@param edu.cwi.UserAccount account

@template.jte.accountLayout(
    title = title,
    account = @`
        @template.jte.userAccount(
            name = account.name(),
            balance = account.formatBalance()
        )
    `,
    detail = @`
        @template.jte.transactionsList(
            transactions = account.transactions()
        )
    `
)
