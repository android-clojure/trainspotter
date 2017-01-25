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
              [clj-time.core :as t])
    (:import android.widget.EditText))

;; We execute this function to import all subclasses of R class. This gives us
;; access to all application resources.
(res/import-all)

(defn add-train-to-watch [from to ^org.joda.time.DateTime date-time]
  (let [id-to-add
        (train/get-id (api/get-schedule-for-train from to date-time))]
    id-to-add))

(defn find-train [activity]
  (let [^EditText from (.getText (find-view activity ::from))
        ^EditText to (.getText (find-view activity ::to))
        ^EditText date (.getText (find-view activity ::date))
        ^EditText dep-time (.getText (find-view activity ::time))]
    (add-train-to-watch
      from
      to
      (utils/str-to-date-time (str date "T" dep-time ".000Z")))))

;; This is how an Activity is defined. We create one and specify its onCreate
;; method. Inside we create a user interface that consists of an edit and a
;; button. We also give set callback to the button.
(defactivity org.trainspotter.MainActivity
  :key :main

  (onCreate [this bundle]
    (.superOnCreate this bundle)
    (neko.debug/keep-screen-on this)
    (on-ui
      (set-content-view! (*a)
        [:linear-layout {:orientation :vertical
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
                       :hint "05:17:00"
                       :layout-width :wrap}]
         [:button {:text "find train"
                   :on-click (fn [_] (find-train (*a)))}]]]))))
