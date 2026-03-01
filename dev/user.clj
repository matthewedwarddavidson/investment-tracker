(ns user
  (:require [integrant.repl :as ig-repl]
            [investment-tracker.system :as system]))

(ig-repl/set-prep! #(system/system-config :dev))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
