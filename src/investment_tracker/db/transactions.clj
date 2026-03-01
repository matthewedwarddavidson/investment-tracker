(ns investment-tracker.db.transactions
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn find-by-id
  "Returns a single transaction by ID, or nil."
  [datasource id]
  (sql/get-by-id datasource :transactions id
                 jdbc/unqualified-snake-kebab-opts))

(defn find-all
  "Returns all transactions, optionally filtered by symbol."
  ([datasource]
   (find-all datasource nil))
  ([datasource {:keys [symbol]}]
   (if symbol
     (jdbc/execute! datasource
                    ["SELECT * FROM transactions WHERE symbol = ? ORDER BY date"
                     symbol]
                    jdbc/unqualified-snake-kebab-opts)
     (jdbc/execute! datasource
                    ["SELECT * FROM transactions ORDER BY date"]
                    jdbc/unqualified-snake-kebab-opts))))

(defn insert!
  "Inserts a transaction record and returns the created row."
  [datasource {:keys [symbol type quantity price date]}]
  (let [result (sql/insert! datasource :transactions
                            {:symbol   symbol
                             :type     type
                             :quantity quantity
                             :price    price
                             :date     date}
                            jdbc/unqualified-snake-kebab-opts)
        id     (get result (keyword "last-insert-rowid()"))]
    (find-by-id datasource id)))

(defn delete!
  "Deletes a transaction by ID. Returns the number of rows affected."
  [datasource id]
  (sql/delete! datasource :transactions {:id id}
               jdbc/unqualified-snake-kebab-opts))
