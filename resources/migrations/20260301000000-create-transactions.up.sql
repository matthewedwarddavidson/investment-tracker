CREATE TABLE transactions (
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  symbol     TEXT    NOT NULL,
  type       TEXT    NOT NULL CHECK (type IN ('buy', 'sell')),
  quantity   REAL    NOT NULL CHECK (quantity > 0),
  price      REAL    NOT NULL CHECK (price >= 0),
  date       TEXT    NOT NULL,
  created_at TEXT    NOT NULL DEFAULT (datetime('now'))
);

CREATE INDEX idx_transactions_symbol ON transactions (symbol);
CREATE INDEX idx_transactions_date   ON transactions (date);
