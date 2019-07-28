(ns contacts-api-clojure.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [reitit.ring :as ring]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]))

(def contacts (atom [{:id "1" :name "Faisal" :twitter "faisalhasnainz"}
                     {:id "2" :name "Paul" :twitter "paulg"}]))

(defn get-contacts [request]
  (response @contacts))

(defn get-contact [{{id :id} :path-params}]
  (response (first (filter #(= (:id %) id) @contacts))))

(defn add-contact [{contact :body}]
  (swap! contacts conj contact)
  (response contact))

(defn remove-contact [{{id :id} :path-params}]
  (let [contact (first (filter #(= (:id %) id) @contacts))]
    (swap! contacts (fn [arr] (remove #(= (:id %) id) arr)))
    (response contact)))

(def router
  (ring/ring-handler
   (ring/router
    [["/" {:get get-contacts :post add-contact}]
     ["/:id" {:get get-contact :delete remove-contact}]])))

(defn -main [& args]
  (run-jetty (->  #'router (wrap-json-body) (wrap-json-response)) {:port 8000})
  (println "Web server listening on port: 8000"))