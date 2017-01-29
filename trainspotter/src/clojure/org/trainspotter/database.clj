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
               :train_id "integer not null unique"
               :station "text not null"}}}))

(def get-db-helper
  (memoize
    (fn [] (db/create-helper trainspotter-schema))))

(defn trainspotter-db [] (db/get-database (get-db-helper) :write))

(defn wipe []
  (log/i "Wiping database!")
  (.delete ^SQLiteDatabase (.db ^TaggedDatabase (trainspotter-db)) "trains" "1" nil))

(defn get-id [entry-seq]
  (:_id entry-seq))

(defn get-train [train-id]
  (log/i "checkin if train is in database:" train-id)
   (first
     (db/query-seq
       (trainspotter-db)
       :trains
       {:train_id train-id})))

(defn get-trains []
  (db/query-seq
    (trainspotter-db)
    [:train_id :station]
    :trains
    nil))

(defn add-train [train-id station]
  (log/i "adding train to database:" train-id station)
  (let [return (db/insert
                 (trainspotter-db)
                 :trains {:train_id train-id
                          :station station})]
    (assert (not= -1 return) "db insert failed")
    (log/w "train added with db id:" return)
    return))
