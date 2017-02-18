(ns org.trainspotter.utils
  (:require [clj-time.core :as t]
            [clj-time.format :as f]))

(def date-time-utc-formatter (f/with-zone (f/formatters :date-time) t/utc))
(def date-utc-formatter (f/with-zone (f/formatters :date) t/utc))
(def hour-minute-formatter (f/with-zone (f/formatters :hour-minute) t/utc))

(defn date-time-to-str [^org.joda.time.DateTime date-time]
  (f/unparse date-time-utc-formatter date-time))

(defn date-to-str [^org.joda.time.DateTime date-time]
  (f/unparse date-utc-formatter date-time))

(defn date-time-to-hhmm [^org.joda.time.DateTime date-time]
  (f/unparse hour-minute-formatter date-time))

(defn str-to-date-time [date-str]
  (f/parse date-time-utc-formatter date-str))
