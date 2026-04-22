(require '[babashka.http-client :as http])
(require '[clojure.java.io :as io])
(require '[cheshire.core :as json])

(let [resp (http/post "http://localhost:8080"
                      {:headers {:content-type "application/json"}
                       :body (json/encode {:text "Hello!"})})]
  (prn :response resp))

(let [resp (http/post "http://localhost:8080"
                      {:headers {:content-type "application/json"}
                       :body (json/encode {:text "Bonjour!"})})]
  (prn :response resp))

(let [resp (http/post "http://localhost:8080"
                      {:headers {:content-type "application/json"}
                       :body (json/encode {:text "Privet!"})})]
  (prn :response resp))

(let [resp (http/get "http://localhost:8080")
      body (:body resp)
      messages (json/decode body)]
  (doseq [msg messages]
    (prn :message msg)))
