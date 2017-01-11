(ns org.trainspotter.utils
  (:require [clj-time.core :as t]
            [clj-time.format :as f]))

(def date-time-utc-formatter (f/with-zone (f/formatters :date-time) t/utc))
(def date-utc-formatter (f/with-zone (f/formatters :date) t/utc))

(defn date-time-to-str [^org.joda.time.DateTime date-time]
  (f/unparse date-time-utc-formatter date-time))

(defn date-to-str [^org.joda.time.DateTime date-time]
  (f/unparse date-utc-formatter date-time))
