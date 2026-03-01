(ns investment-tracker.api.transactions
  (:require [investment-tracker.db.transactions :as txns]))

(defn list-transactions
  "Handles GET /transactions with optional ?symbol= filter."
  [{:keys [datasource]} request]
  (let [symbol (get-in request [:query-params "symbol"])
        results (txns/find-all datasource (when symbol {:symbol symbol}))]
    {:status 200
     :body   results}))

(defn create-transaction
  "Handles POST /transactions."
  [{:keys [datasource]} request]
  (let [body    (:body-params request)
        created (txns/insert! datasource body)]
    {:status 201
     :body   created}))

(defn get-transaction
  "Handles GET /transactions/:id."
  [{:keys [datasource]} request]
  (let [id    (parse-long (get-in request [:path-params :id]))
        found (txns/find-by-id datasource id)]
    (if found
      {:status 200
       :body   found}
      {:status 404
       :body   {:error "Transaction not found"}})))

(defn delete-transaction
  "Handles DELETE /transactions/:id."
  [{:keys [datasource]} request]
  (let [id (parse-long (get-in request [:path-params :id]))]
    (txns/delete! datasource id)
    {:status 204
     :body   nil}))
