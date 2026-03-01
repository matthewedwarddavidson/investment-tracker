(ns investment-tracker.db.transactions-test
  (:require [clojure.test :refer [deftest is testing]]
            [investment-tracker.db.transactions :as txns]
            [investment-tracker.test-helpers :as h]))

(def sample-transaction
  {:symbol   "VWRL"
   :type     "buy"
   :quantity 10.0
   :price    85.50
   :date     "2026-01-15"})

(deftest insert-and-find-test
  (h/with-test-datasource
    (fn [ds]
      (testing "inserts a transaction and retrieves it by ID"
        (let [created (txns/insert! ds sample-transaction)
              found   (txns/find-by-id ds (:id created))]
          (is (= "VWRL" (:symbol found)))
          (is (= "buy" (:type found)))
          (is (= 10.0 (:quantity found)))
          (is (= 85.50 (:price found)))))

      (testing "find-all returns all inserted transactions"
        (txns/insert! ds (assoc sample-transaction :symbol "ISFA" :date "2026-02-01"))
        (let [all (txns/find-all ds)]
          (is (= 2 (count all)))))

      (testing "find-all filters by symbol"
        (let [vwrl-only (txns/find-all ds {:symbol "VWRL"})]
          (is (= 1 (count vwrl-only)))
          (is (= "VWRL" (:symbol (first vwrl-only)))))))))

(deftest delete-test
  (h/with-test-datasource
    (fn [ds]
      (testing "deletes a transaction by ID"
        (let [created (txns/insert! ds sample-transaction)]
          (txns/delete! ds (:id created))
          (is (nil? (txns/find-by-id ds (:id created)))))))))
