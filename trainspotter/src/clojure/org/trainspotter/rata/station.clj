(ns org.trainspotter.rata.station)

(defn get-name [station]
  (:stationName station))

(defn get-short-code [station]
  (:stationShortCode station))
