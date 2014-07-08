(ns nowayhoze.mintpal
  (require [clj-http.client :as client]
           [pandect.core :as sha]
           [nowayhoze.cryptsy :as cpt]
           [nowayhoze.cryptsy-markets :as cm]))


(defn get-summary[]
  (:body (client/get "https://api.mintpal.com/v2/market/summary/" {:as :json})))
(client/get (str "https://api.mintpal.com/v2/market/orders/" "DRK" "/" "BTC" "/BUY/5"))
(client/get (str "https://api.mintpal.com/v2/market/orders/" "DRK" "/" "BTC" "/SELL/5"))
(defn get-buy-orders[coin exhange]
  (try
    (map cpt/vals-to-float (-> (client/get (str "https://api.mintpal.com/v2/market/orders/"coin"/"exhange"/BUY/2") {:as :json})
      :body :data))
    (catch Exception e {})))

(defn get-sell-orders[coin exhange]
  (try
    (map cpt/vals-to-float (-> (client/get (str "https://api.mintpal.com/v2/market/orders/"coin"/"exhange"/SELL/2") {:as :json})
      :body :data))
    (catch Exception e {})))

(get-buy-orders  "DRK" "LTC")
(get-sell-orders  "DRK" "BTC" )


(defn get-markets[market-vector]
  (into {}
    (for[market-name market-vector]
      (let [coin (:primarycode (cm/markets market-name) )
            market-coin (:secondarycode (cm/markets market-name))]
        { market-name {:buy-ord (get-buy-orders coin market-coin ) :sell-ord (get-sell-orders coin market-coin ) }} ))))


(let [coin (:primarycode (cm/markets :LTC-BTC) )
            market-coin (:secondarycode (cm/markets :LTC-BTC))] [coin market-coin])

(:primarycode (cm/markets :LTC-BTC))
(get-markets [:LTC-BTC :DRK-BTC])



(def raw-ticker (:data (get-summary)))

raw-ticker

(into {} (for [r raw-ticker] { (str (:code r) "-" (:exchange r))  r } ))

(defn get-saldo[]
"TODO: return saldo here "
  )


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
