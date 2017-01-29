(ns org.trainspotter.t-database
  (:require [clojure.test :refer :all]
            [org.trainspotter.database :as trainspotter]
            [neko.data.sqlite :as db])
  (:import
    [android.app Activity]
    org.robolectric.RuntimeEnvironment
    neko.App))

(set! App/instance RuntimeEnvironment/application)

(defn db-fixture [f]
  (assert (= () (db/query-seq (trainspotter/trainspotter-db) :trains {})))
  (f)
  (trainspotter/wipe))

(use-fixtures :each db-fixture)

(deftest add-train
  (let [train-id 2468
        station "LPV"]
    (trainspotter/add-train train-id station)
    (is (= (db/query-seq (trainspotter/trainspotter-db) :trains {:_id 1})
           (list {:_id 1
                  :train_id train-id
                  :station station})))))
(deftest get-train
  (let [train-id 2468
        station "LPV"]
    (trainspotter/add-train train-id station)
    (is (= (trainspotter/get-train train-id)
           {:_id 1
            :train_id train-id
            :station station}))))

(deftest get-train-ids
  (let [train-ids [1 2 3 4 5]]
    (doseq [train-id train-ids]
      (trainspotter/add-train train-id "LPV"))
    (is (= (trainspotter/get-train-ids)
           train-ids))))
