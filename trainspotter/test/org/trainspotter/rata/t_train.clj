(ns org.trainspotter.rata.t-train
  (:require [clojure.test :refer :all]
            [org.trainspotter.rata.train :as train])
  (:import
    [android.app Activity]
    org.robolectric.RuntimeEnvironment
    neko.App))

(set! App/instance RuntimeEnvironment/application)

(deftest cancelled?
  (let [train-data
        {:trainNumber 8461 :departureDate "2017-01-30" :operatorUICCode 10 :operatorShortCode "vr" :trainType "HL" :trainCategory "Commuter" :commuterLineID "U" :runningCurrently false :cancelled false :version 208446841804 :timetableType "REGULAR" :timetableAcceptanceDate "2016-11-11T11:45:52.000Z" :timeTableRows
         [{:stationShortCode "HKI" :stationUICCode 1 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "13" :cancelled false :scheduledTime "2017-01-30T05:05:00.000Z" :actualTime "2017-01-30T05:05:11.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "ARRIVAL" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "DEPARTURE" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:16:24.000Z" :actualTime "2017-01-30T05:16:07.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:17:00.000Z" :actualTime "2017-01-30T05:17:04.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "KKN" :stationUICCode 63 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "3" :cancelled false :scheduledTime "2017-01-30T05:46:00.000Z" :actualTime "2017-01-30T05:46:47.000Z" :differenceInMinutes 1 :causes []}]}
        ]
    (is (= false (train/cancelled? train-data "LPV"))))
  (let [train-data
        {:trainNumber 8461 :departureDate "2017-01-30" :operatorUICCode 10 :operatorShortCode "vr" :trainType "HL" :trainCategory "Commuter" :commuterLineID "U" :runningCurrently false :cancelled false :version 208446841804 :timetableType "REGULAR" :timetableAcceptanceDate "2016-11-11T11:45:52.000Z" :timeTableRows
         [{:stationShortCode "HKI" :stationUICCode 1 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "13" :cancelled false :scheduledTime "2017-01-30T05:05:00.000Z" :actualTime "2017-01-30T05:05:11.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "ARRIVAL" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "DEPARTURE" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:16:24.000Z" :actualTime "2017-01-30T05:16:07.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled true :scheduledTime "2017-01-30T05:17:00.000Z" :actualTime "2017-01-30T05:17:04.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "KKN" :stationUICCode 63 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "3" :cancelled false :scheduledTime "2017-01-30T05:46:00.000Z" :actualTime "2017-01-30T05:46:47.000Z" :differenceInMinutes 1 :causes []}]}
        ]
    (is (= true (train/cancelled? train-data "LPV")))))

(deftest get-stop
  (let [train-data
        {:trainNumber 8461 :departureDate "2017-01-30" :operatorUICCode 10 :operatorShortCode "vr" :trainType "HL" :trainCategory "Commuter" :commuterLineID "U" :runningCurrently false :cancelled false :version 208446841804 :timetableType "REGULAR" :timetableAcceptanceDate "2016-11-11T11:45:52.000Z" :timeTableRows
         [{:stationShortCode "HKI" :stationUICCode 1 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "13" :cancelled false :scheduledTime "2017-01-30T05:05:00.000Z" :actualTime "2017-01-30T05:05:11.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "ARRIVAL" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "DEPARTURE" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:16:24.000Z" :actualTime "2017-01-30T05:16:07.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:17:00.000Z" :actualTime "2017-01-30T05:17:04.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "KKN" :stationUICCode 63 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "3" :cancelled false :scheduledTime "2017-01-30T05:46:00.000Z" :actualTime "2017-01-30T05:46:47.000Z" :differenceInMinutes 1 :causes []}]}
        ]
    (is (= {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:17:00.000Z" :actualTime "2017-01-30T05:17:04.000Z" :differenceInMinutes 0 :causes []} (train/get-stop train-data "LPV" :departure))))
  (let [train-data
        {:trainNumber 8461 :departureDate "2017-01-30" :operatorUICCode 10 :operatorShortCode "vr" :trainType "HL" :trainCategory "Commuter" :commuterLineID "U" :runningCurrently false :cancelled false :version 208446841804 :timetableType "REGULAR" :timetableAcceptanceDate "2016-11-11T11:45:52.000Z" :timeTableRows
         [{:stationShortCode "HKI" :stationUICCode 1 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "13" :cancelled false :scheduledTime "2017-01-30T05:05:00.000Z" :actualTime "2017-01-30T05:05:11.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "ARRIVAL" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "DEPARTURE" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:16:24.000Z" :actualTime "2017-01-30T05:16:07.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:17:00.000Z" :actualTime "2017-01-30T05:17:04.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "KKN" :stationUICCode 63 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "3" :cancelled false :scheduledTime "2017-01-30T05:46:00.000Z" :actualTime "2017-01-30T05:46:47.000Z" :differenceInMinutes 1 :causes []}]}
        ]
    (is (= {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:16:24.000Z" :actualTime "2017-01-30T05:16:07.000Z" :differenceInMinutes 0 :causes []} (train/get-stop train-data "LPV" :arrival)))))

(deftest get-late-info
  (let [train-data
        {:trainNumber 8461 :departureDate "2017-01-30" :operatorUICCode 10 :operatorShortCode "vr" :trainType "HL" :trainCategory "Commuter" :commuterLineID "U" :runningCurrently false :cancelled false :version 208446841804 :timetableType "REGULAR" :timetableAcceptanceDate "2016-11-11T11:45:52.000Z" :timeTableRows
         [{:stationShortCode "HKI" :stationUICCode 1 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "13" :cancelled false :scheduledTime "2017-01-30T05:05:00.000Z" :actualTime "2017-01-30T05:05:11.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "ARRIVAL" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "DEPARTURE" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:16:24.000Z" :actualTime "2017-01-30T05:17:07.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:17:00.000Z" :actualTime "2017-01-30T05:17:04.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "KKN" :stationUICCode 63 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "3" :cancelled false :scheduledTime "2017-01-30T05:46:00.000Z" :actualTime "2017-01-30T05:46:47.000Z" :differenceInMinutes 1 :causes []}]}
        ]
    (is (= {:late false :differenceInMinutes 0 :causes []} (train/get-late-info train-data "LPV"))))
  (let [train-data
        {:trainNumber 8461 :departureDate "2017-01-30" :operatorUICCode 10 :operatorShortCode "vr" :trainType "HL" :trainCategory "Commuter" :commuterLineID "U" :runningCurrently false :cancelled false :version 208446841804 :timetableType "REGULAR" :timetableAcceptanceDate "2016-11-11T11:45:52.000Z" :timeTableRows
         [{:stationShortCode "HKI" :stationUICCode 1 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "13" :cancelled false :scheduledTime "2017-01-30T05:05:00.000Z" :actualTime "2017-01-30T05:05:11.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "ARRIVAL" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "VMO" :stationUICCode 847 :countryCode "FI" :type "DEPARTURE" :trainStopping false :commercialTrack "" :cancelled false :scheduledTime "2017-01-30T05:12:53.000Z" :actualTime "2017-01-30T05:13:24.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:16:24.000Z" :actualTime "2017-01-30T05:16:07.000Z" :differenceInMinutes 0 :causes []}
          {:stationShortCode "LPV" :stationUICCode 68 :countryCode "FI" :type "DEPARTURE" :trainStopping true :commercialStop true :commercialTrack "1" :cancelled false :scheduledTime "2017-01-30T05:17:00.000Z" :actualTime "2017-01-30T05:18:04.000Z" :differenceInMinutes 1 :causes []}
          {:stationShortCode "KKN" :stationUICCode 63 :countryCode "FI" :type "ARRIVAL" :trainStopping true :commercialStop true :commercialTrack "3" :cancelled false :scheduledTime "2017-01-30T05:46:00.000Z" :actualTime "2017-01-30T05:46:47.000Z" :differenceInMinutes 1 :causes []}]}
        ]
    (is (= {:late true :differenceInMinutes 1 :causes []} (train/get-late-info train-data "LPV")))))
