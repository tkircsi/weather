(ns weather.core)
(def filename "weather.csv")

(def weather-record-keys [:year :month :day :hour :minute :temp])

(defn str->int
  [str]
  (Integer. str))


(defn str->float
  [str]
  (Float. str))

(def conversions {:year str->int
                  :month str->int
                  :day str->int
                  :hour str->int
                  :minute str->int
                  :temp str->float})

(defn convert
  [weather-record-key value]
  ((get conversions weather-record-key) value))

(defn parse
  "Parse CSV."
  [string]
  (map #(clojure.string/split % #";")
       (clojure.string/split string #"\r\n")))

(defn mapify
  "Return a seq of maps"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [weather-record-key value]]
                   (assoc row-map weather-record-key (convert weather-record-key value)))
                 {}
                 (map vector weather-record-keys unmapped-row)))
       rows))

