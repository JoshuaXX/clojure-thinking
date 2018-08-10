(require 'cemerick.pomegranate.aether)

(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(defproject clojure-thinking "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 ;; profiling
                 [com.taoensso/tufte "2.0.1"]
                 ;; logging
                 [com.taoensso/timbre "4.10.0"]
                 ;; redis client lib
                 [com.taoensso/carmine "2.18.1"]
                 ;; configuration lib
                 [yogthos/config "1.1.1"]
                 ;; combinatorics
                 [org.clojure/math.combinatorics "0.1.4"]]
  :source-paths ["src"]
  :resource-paths ["resources"])
