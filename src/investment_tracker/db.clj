(ns investment-tracker.db
  (:require [integrant.core :as ig]
            [next.jdbc.connection :as connection]
            [taoensso.timbre :as log])
  (:import [com.zaxxer.hikari HikariDataSource]))

(defmethod ig/init-key :investment-tracker.db/datasource
  [_ {:keys [jdbc-url]}]
  (log/info "Starting connection pool for" jdbc-url)
  (connection/->pool HikariDataSource
                     {:jdbcUrl jdbc-url}))

(defmethod ig/halt-key! :investment-tracker.db/datasource
  [_ datasource]
  (log/info "Stopping connection pool")
  (.close datasource))
