(ns investment-tracker.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]))

(defn read-config
  "Reads and resolves the EDN configuration for the given profile."
  [profile]
  (aero/read-config (io/resource "config.edn") {:profile profile}))
