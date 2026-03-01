(ns investment-tracker.handler
  (:require [integrant.core :as ig]
            [reitit.ring :as ring]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.core :as m]
            [investment-tracker.api.transactions :as txn-api]))

(defn health-handler
  "Returns a simple health-check response."
  [_request]
  {:status 200
   :body   {:status "ok"}})

(defn- app-routes [config]
  [["/health" {:get {:handler health-handler}}]
   ["/transactions" {:get  {:handler (partial txn-api/list-transactions config)}
                     :post {:handler (partial txn-api/create-transaction config)}}]
   ["/transactions/:id" {:get    {:handler (partial txn-api/get-transaction config)}
                         :delete {:handler (partial txn-api/delete-transaction config)}}]])

(defn- create-app
  "Builds the Ring handler from reitit routes."
  [config]
  (ring/ring-handler
   (ring/router
    (app-routes config)
    {:data {:muuntaja   m/instance
            :middleware [muuntaja/format-middleware]}})
   (ring/create-default-handler)))

(defmethod ig/init-key :investment-tracker.handler/routes
  [_ config]
  (create-app config))
