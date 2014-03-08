(ns thy-dungeonman.core-test
  (:require [clojure.test :refer :all]
            [thy-dungeonman.core :refer :all]
            [thy-dungeonman.game :refer [new-game]]
            [thy-dungeonman.command :refer [process-input]]))

(deftest a-new-game
  (testing "game starts in main room"
    (is (= (:location (new-game)) :main)))
  (testing "game dispatches generic command"
    (is (= (:message (process-input "look foot" (new-game)))
           "It looketh pretty awesome."))))

(deftest main-room
  (testing "look flask message in main room"
    (is (= (:message (process-input "look flask" (new-game)))
           "Looks like you could quaff some serious mead out of that thing."))))

(defn make-test-inputs
  "makes a reader function that will input consecutively the values in a list"
  [commands]
  (let [index (atom -1)]
    (fn []
      (swap! index inc)
      (get commands @index))))

(deftest game-write-and-read
  (testing "write message"
    (is (let [output-stream (atom "")
              writer (fn [text] (swap! output-stream (fn [_] text)))
              reader (make-test-inputs ["exit"])]
          (run-game writer reader)
          (= @output-stream "Ye find yeself in yon dungeon. Ye see a SCROLL. Behind ye scroll is a FLASK. Obvious exits are NORTH, SOUTH and DENNIS."))))
  (testing "read message"
    (is (let [output-stream (atom "")
              writer (fn [text] (swap! output-stream (fn [_] text)))
              reader (make-test-inputs ["get scroll"
                                        "look scroll"
                                        "exit"])]
          (run-game writer reader)
          (= @output-stream "Ye seeth nothing wheretofore it went ZAP.")))))
