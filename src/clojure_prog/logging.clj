(ns clojure-prog.logging
  (:require [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.3rd-party.rolling :as roll-appender]))



(def appender-one (roll-appender/rolling-appender :path "./timbre-rolling.log"
                                                  :pattern :daily))

(def appender-two {:enabled?   true
                   :async?     false
                   :min-level  nil
                   :rate-limit [[1 250] [10 5000]]
                   :output-fn  :inherit
                   :fn (fn [data]
                         (let [{:keys [output_]} data
                               formatted-output-str (force output_)]
                           (println formatted-output-str)))})


(def config-one
{:level :debug  ;one of #{:trace :debug :info :warn :error :fatal :report}

 ;; Control log filtering by namespaces/patterns. Useful for turning off
 ;; logging in noisy libraries, etc.:
 :ns-whitelist  ["clojure-prog.*"] #_["my-app.foo-ns"]
 :ns-blacklist  [] #_["taoensso.*"]
 :middleware [] ; (fns [data]) -> ?data, applied left->right
 :appenders {:rolling-appender appender-one
             :console-appender appender-two}})

(def config-two
{:level :trace  ;one of #{:trace :debug :info :warn :error :fatal :report}

 ;; Control log filtering by namespaces/patterns. Useful for turning off
 ;; logging in noisy libraries, etc.:
 :ns-whitelist  ["clojure-prog.*"] #_["my-app.foo-ns"]
 :ns-blacklist  [] #_["taoensso.*"]
 :middleware [] ; (fns [data]) -> ?data, applied left->right
 :appenders {:rolling-appender appender-one
             :console-appender appender-two}})

#_(timbre/with-config config-one
  (timbre/log :trace "Test this logging line"))


#_(timbre/with-config config-two
  (timbre/log :trace "Test this logging line"))
