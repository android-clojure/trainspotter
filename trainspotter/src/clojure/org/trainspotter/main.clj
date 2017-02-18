(ns org.trainspotter.main
    (:require [neko.activity :refer [defactivity set-content-view!]]
              [neko.debug :refer [*a]]
              [neko.notify :refer [toast]]
              [neko.resource :as res]
              [neko.find-view :refer [find-view]]
              [neko.threading :refer [on-ui]]
              [org.trainspotter.rata.api :as api]
              [org.trainspotter.rata.train :as train]
              [org.trainspotter.rata.station :as station]
              [org.trainspotter.utils :as utils]
              [org.trainspotter.database :as db]
              [org.trainspotter.log :as log]
              [clj-time.core :as t])
    (:import android.app.Activity
             android.content.Intent
             ))

;; We execute this function to import all subclasses of R class. This gives us
;; access to all application resources.
(res/import-all)

(defn add-train-to-watch [from to ^org.joda.time.DateTime date-time]
  (let [train-to-add (future (api/get-schedule-for-train from to date-time))
        train-id (train/get-id @train-to-add)
        train-name(train/get-name @train-to-add) ]
    (on-ui (toast (str "train to add: " train-id)))
    (db/add-train train-id from train-name (utils/date-time-to-hhmm date-time))))

(defn find-train [activity]
  (let [from (str (.getText (find-view activity ::from)))
        to (str (.getText (find-view activity ::to)))
        date (str (.getText (find-view activity ::date)))
        dep-time (str (.getText (find-view activity ::time)))]
    (add-train-to-watch
      from
      to
      (utils/str-to-date-time (str date "T" dep-time ":00.000Z")))))

(defn make-keyword [& more]
  {:pre [(not (nil? more))]}
  (keyword
    (str *ns*)
    (clojure.string/join "-" more)))

;; this is train from the database, not from the api
(defn train-view [^Activity ctx train-data]
  (let [train-id (:train_id train-data)
        station (:station train-data)
        train-name (:name train-data)
        departure-time (:time train-data)]
    [:linear-layout {:orientation :horizontal
                     :layout-width :fill
                     :layout-height :wrap}
     [:text-view {:id (make-keyword train-id "station")
                  :text (str station)
                  :layout-width [0 :dp]
                  :layout-weight 1
                  }]
     [:text-view {:id (make-keyword train-id "name")
                  :text (str train-name)
                  :layout-width [0 :dp]
                  :layout-weight 1
                  }]
     [:text-view {:id (make-keyword train-id "time")
                  :text (str departure-time)
                  :layout-width [0 :dp]
                  :layout-weight 1
                  }]
     ]
    )
  )

(defn main-layout [^Activity ctx]
  `[:linear-layout {:orientation :vertical
                   :layout-width :fill
                   :layout-height :wrap}
   [:linear-layout {:orientation :horizontal
                    :layout-width :fill
                    :layout-height :wrap}
    [:edit-text {:id ::from
                 :hint "LPV"
                 :layout-width :wrap}]
    [:edit-text {:id ::to
                 :hint "JRS"
                 :layout-width :wrap}]
    [:edit-text {:id ::date
                 :hint "2017-01-09"
                 :layout-width :wrap}]
    [:edit-text {:id ::time
                 :hint "05:17"
                 :layout-width :wrap}]
    [:button {:text "find train"
              :on-click (fn [_] (find-train ctx))}]]
   ~@(map #(train-view (*a) %) (db/get-trains))
   ])

;; This is how an Activity is defined. We create one and specify its onCreate
;; method. Inside we create a user interface that consists of an edit and a
;; button. We also give set callback to the button.
(defactivity org.trainspotter.MainActivity
  :key :main

  (onCreate
    [this bundle]
    (let [service (Intent. this org.trainspotter.service)]
      (.superOnCreate this bundle)
      (neko.debug/keep-screen-on this)
      (on-ui
        (set-content-view! this (main-layout this)))
      (.startService this service)
      ))
  (onStart
    [this]
    (.superOnStart this)
    )
  (onResume
    [this]
    (do
      (on-ui (.setText (find-view (*a) ::from) "LPV"))
      (on-ui (.setText (find-view (*a) ::to) "JRS"))
      (on-ui (.setText (find-view (*a) ::date) "2017-01-09"))
      (on-ui (.setText (find-view (*a) ::time) "05:17")))
    (.superOnResume this)
    )
  (onPause
    [this]
    (.superOnPause this)
    )
  (onStop
    [this]
    (.superOnStop this)
    )
  (onDestroy
    [this]
    (let [service (Intent. this org.trainspotter.service)]
      (.superOnDestroy this)
      (.stopService this service)
      ))
)

; (.startService (*a) (Intent. (*a) org.trainspotter.service))
; (.stopService (*a) (Intent. (*a) org.trainspotter.service))
