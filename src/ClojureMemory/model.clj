(ns ClojureMemory.model

(:require [noir.session :as session]))

(def empty-board [[\- \- \- \- \-]
                  [\- \- \- \- \-]
                  [\- \- \- \- \-]
                  [\- \- \- \- \-]])

(def memory-board [[\A \B \D \E \C]
                  [\A \B \C \F \G]
                  [\D \G \H \J \I]
                  [\E \F \I \J \H]])

(def init-state {:board empty-board :turn 1 :p1-score 0 :p2-score 0 :player \1})

(defn makeBoard [] (session/put! :mem-board (shuffle [(shuffle [\A \B \D \E \C]) (shuffle [\A \B \C \F \G]) (shuffle [\D \G \H \J \I]) (shuffle [\E \F \I \J \H])])))

(defn makeScoreBoard []
  (session/put! :score-board empty-board)
  )

(defn reset-game! []
  (session/clear!)
  (session/put! :game-state init-state)
  (makeBoard)
  (makeScoreBoard)
  (println (session/get :game-state) "start game")
  (println (session/get :mem-board))
)
  
(defn get-board []
  (:board (session/get :game-state)))

(defn get-memoryboard []
  (session/get :mem-board)
)

(defn get-scoreboard []
  (session/get :score-board)
)

(defn get-board-cell 
  ([row col]
    (get-board-cell (get-board) row col))
  ([board row col]
    (get-in board [row col])))

(defn set-board-score [row col]
  (session/put! :score-board (assoc-in (session/get :score-board) [row col] \=))
)

(defn get-turn []
  (:turn (session/get :game-state)))

(defn get-player []
  (:player (session/get :game-state)))

(defn get-player-score [player]
  (if (= player \1)
    (if(nil? (session/get :p1-score))
      0
      (session/get :p1-score)
    )
    (if(nil? (session/get :p2-score))
      0
      (session/get :p2-score)
    )
  )
)

(defn get-rowInfo []
  (session/get :temprow) 
  )
(defn get-colInfo []
  (session/get :tempcol)
  )

(defn other-player 
  ([] (other-player (get-player)))
  ([player] (if (= player \1) \2 \1))) 

(defn setScore [player]
  (if (= player \1)
    (if (nil? (session/get :p1-score))
      (session/put! :p1-score 2)
      (session/put! :p1-score (+ (session/get :p1-score) 2))
    ) 
    (if (nil? (session/get :p2-score))
      (session/put! :p2-score 2)
      (session/put! :p2-score (+ (session/get :p2-score) 2))
    )
  )
)

(defn checkIfEqual? [player val1 val2]
  (if (= val1 val2) 
    (do (setScore player) "true")
    "false"
  )
)

(defn full-board?
  ([] (full-board? (get-board)))
  ([board] (let [all-cells (apply concat board)]
             (not-any? #(= % \-) all-cells))))

(defn new-state [row col old-state]
  (if (not (full-board? (:board old-state)))
    {:board (assoc-in (:board old-state) [row col] (get-board-cell (get-memoryboard) row col))
     :turn (session/get :turn)
     :player (:player old-state)
     }
    old-state))

(defn reverse-state [row col old-state switchplayer]
  (if (= (not (full-board? (:board old-state))))
    {:board (assoc-in (:board old-state) [row col] \-)
     :turn (session/get :turn)
     :player (if (= switchplayer true) (other-player) (:player old-state))
     }
    old-state)
  )

(defn hideValues [row col switchplayer]
    (session/swap! (fn [session-map] (assoc session-map :game-state (reverse-state row col (:game-state session-map) switchplayer))))
)

(defn play!
  ([row col]
    (session/put! :turn 2)
    (session/put! :temprow row)
    (session/put! :tempcol col)
    (session/swap! (fn [session-map] (assoc session-map :game-state (new-state row col (:game-state session-map)))))
    (println (session/get :game-state) "first turn")
    )
  ([row col second]
   (let [memboard (get-memoryboard)]
	(if(= (checkIfEqual? (get-player) (get-board-cell memboard row col) (get-board-cell memboard (session/get :temprow) (session/get :tempcol))) "true")
	  (do
		(session/put! :turn 1)
		(session/swap! (fn [session-map] (assoc session-map :game-state (new-state row col (:game-state session-map)))))
		(set-board-score row col)
		(set-board-score (session/get :temprow)(session/get :tempcol))
		(println (session/get :game-state) "game state true")
	  )
	  (do
		(session/put! :turn 1)
		;;(session/swap! (fn [session-map] (assoc session-map :game-state (new-state row col (:game-state session-map)))))
		 (hideValues (session/get :temprow)(session/get :tempcol) false)
		(hideValues row col true)
		(println (session/get :game-state) "game-state false")
	  )
	)
	)
  )
)

(defn checkWinner []
 (cond (> (get-player-score \1) (get-player-score \2)) \1
    (< (get-player-score \1) (get-player-score \2)) \2
    :else "Draw!"
    )
  )