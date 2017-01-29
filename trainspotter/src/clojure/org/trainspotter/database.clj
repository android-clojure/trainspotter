(ns org.trainspotter.database
  (:require [neko.data.sqlite :as db]
            [org.trainspotter.log :as log])
  (:import android.database.sqlite.SQLiteDatabase
          neko.data.sqlite.TaggedDatabase))

(def trainspotter-schema
  (db/make-schema
    :name "trainspotter.db"
    :version 1
    :tables {:trains
             {:columns
              {:_id "integer primary key"
               :train_id "integer not null"}}}))

(def get-db-helper
  (memoize
    (fn [] (db/create-helper trainspotter-schema))))

(defn trainspotter-db [] (db/get-database (get-db-helper) :write))

(defn wipe []
  (log/i "Wiping database!")
  (.delete ^SQLiteDatabase (.db ^TaggedDatabase (trainspotter-db)) "trains" "1" nil))

(defn get-id [entry-seq]
  (:_id entry-seq))

(defn add-train [train-id]
  (log/d "add train to watch:" train-id)
  (db/insert (trainspotter-db) :trains {:train_id train-id}))
