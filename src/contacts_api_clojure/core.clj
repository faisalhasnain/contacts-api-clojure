(ns contacts-api-clojure.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [compojure.coercions :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]))

(def contacts [{:id 1 :name "Faisal" :age 28}
               {:id 2 :name "Sana" :age 23}])

(defn get-contacts []
  contacts)

(defn get-contact [id]
  (first (filter #(= (:id %) id) contacts)))

(defn render-response [resp]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    (json/write-str resp)})

(defroutes app-routes
  (GET "/" [] (render-response (get-contacts)))
  (GET "/:id" [id :<< as-int] (render-response (get-contact id)))
  (route/not-found "Error, page not found!"))

(defn -main [& args]
  (run-server #'app-routes {:port 8000})
  (println "Web server listening on port: 8000"))