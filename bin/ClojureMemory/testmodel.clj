(ns ClojureMemory.testmodel

  (:use ClojureMemory.model)
  (:use clojure.test)
  (:require [ClojureMemory.testdata :as td]))

(deftest get-board-cell-test
  (let [testboard [[\A \B \D \E \C]
							     [\A \B \C \F \G]
							     [\D \G \H \J \I]
							     [\E \F \I \J \H]]]
    (is (= (get-board-cell testboard 0 0) \A))
    (is (= (get-board-cell testboard 0 1) \B))
    (is (= (get-board-cell testboard 1 1) \B))
    (is (= (get-board-cell testboard 2 3) \J)))
)

(deftest full-board-test
  (is (full-board? (td/full-board)) true)
  (is (full-board? (td/non-full-board)) false)
)

(deftest checkIfEqualTest
  (binding [noir.session/*noir-session* (atom {})]
  (is (checkIfEqual? \1 (get-board-cell (td/combination) 0 0) (get-board-cell (td/combination) 1 0)) true)
  (is (checkIfEqual? \1 (get-board-cell (td/combination) 0 0) (get-board-cell (td/combination) 0 1)) false)
  )
)

(deftest )


(comment
(defmacro defboardtest [name winfn positives negatives]
  `(deftest ~name
     (doseq [player# [\X \O]]     
       (doseq [board# (~positives player#)]
         (is (= (~winfn board# player#) true) 
             (str "Player " player# " should win with board " board#)))
       (doseq [board# (~negatives player#)]
         (is (= (~winfn board# player#) false)
             (str "Player " player# " should not win with board " board#))))))
)

(comment
(deftest full-board?-test
  (doseq [player [\X \O]]
    (doseq [board (td/full-boards player)]
      (is (= (full-board? board) true)
          (str "Board should be considered full, but isn't: " board)))
    (doseq [board (td/no-full-boards player)]
      (is (= (full-board? board) false)
          (str "Board should not be considered full, but is: " board)))))
)

(comment
(deftest scenario1-test
  "it should not be possible to choose a cell that is already taken"
  (binding [noir.session/*noir-session* (atom {})]
    (reset-game!)
    (play! 0 0)
    (is (= (get-board-cell 0 0) \X))
    (play! 0 1)
    (is (= (get-board-cell 0 1) \O))
    (play! 0 2)
    (is (= (get-board-cell 0 2) \X))
    (is (= (get-player) \O))
    (play! 0 0)
    (is (= (get-board-cell 0 0) \X) "value of cell 0 0 should still be X")
    (is (= (get-player) \O) "player should still be O")
    (reset-game!)))
)

;; exercise: add deftest for function winner?
;; exercise: macro for defining test scenarios which resets game automatically at beginning and end
;; exercise: refactor scenario1-test using the macro
;; exercise: more scenario's
;;       - player X wins
;;       - player O wins
;;       - it's a draw
