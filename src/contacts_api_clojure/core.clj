(ns contacts-api-clojure.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [compojure.coercions :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]))

(def contacts (atom [{:id 1 :name "Faisal" :twitter "faisalhasnainz"}
                     {:id 2 :name "Paul" :twitter "paulg"}]))

(defn get-contacts []
  @contacts)

(defn get-contact [id]
  (first (filter #(= (:id %) id) @contacts)))

(defn add-contact [contact]
  (swap! contacts conj contact)
  contact)

(defn remove-contact [id]
  (let [contact (get-contact id)]
    (swap! contacts (fn [arr] (remove #(= (:id %) id) arr)))
    contact))

(defroutes app-routes
  (GET "/" [] (response (get-contacts)))
  (GET "/:id" [id :<< as-int] (response (get-contact id)))
  (POST "/" req (response (add-contact (:body req))))
  (DELETE "/:id" [id :<< as-int] (response (remove-contact id)))
  (route/not-found "Error, page not found!"))

(defn -main [& args]
  (run-server (->  #'app-routes (wrap-json-body) (wrap-json-response)) {:port 8000})
  (println "Web server listening on port: 8000"))