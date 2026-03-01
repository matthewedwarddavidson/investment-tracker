(ns investment-tracker.api.transactions-test
  (:require [clojure.test :refer [deftest is testing]]
            [investment-tracker.api.transactions :as txn-api]
            [investment-tracker.test-helpers :as h]))

(def sample-body
  {:symbol   "VWRL"
   :type     "buy"
   :quantity 10.0
   :price    85.50
   :date     "2026-01-15"})

(deftest create-and-list-test
  (h/with-test-datasource
    (fn [ds]
      (let [config {:datasource ds}]
        (testing "POST /transactions returns 201 with created record"
          (let [response (txn-api/create-transaction config {:body-params sample-body})]
            (is (= 201 (:status response)))
            (is (= "VWRL" (get-in response [:body :symbol])))))

        (testing "GET /transactions returns all transactions"
          (let [response (txn-api/list-transactions config {:query-params {}})]
            (is (= 200 (:status response)))
            (is (= 1 (count (:body response))))))

        (testing "GET /transactions?symbol= filters by symbol"
          (txn-api/create-transaction config {:body-params (assoc sample-body :symbol "ISFA")})
          (let [response (txn-api/list-transactions config {:query-params {"symbol" "VWRL"}})]
            (is (= 1 (count (:body response))))))))))

(deftest get-and-delete-test
  (h/with-test-datasource
    (fn [ds]
      (let [config   {:datasource ds}
            created  (:body (txn-api/create-transaction config {:body-params sample-body}))
            id       (str (:id created))]
        (testing "GET /transactions/:id returns the transaction"
          (let [response (txn-api/get-transaction config {:path-params {:id id}})]
            (is (= 200 (:status response)))
            (is (= "VWRL" (get-in response [:body :symbol])))))

        (testing "DELETE /transactions/:id returns 204"
          (let [response (txn-api/delete-transaction config {:path-params {:id id}})]
            (is (= 204 (:status response)))))

        (testing "GET /transactions/:id returns 404 after deletion"
          (let [response (txn-api/get-transaction config {:path-params {:id id}})]
            (is (= 404 (:status response)))))))))
