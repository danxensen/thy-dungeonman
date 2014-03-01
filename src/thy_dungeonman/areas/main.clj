(ns thy-dungeonman.areas.main
  (:use [thy-dungeonman.areas.core :only [->Area]]
        [thy-dungeonman.command :only [make-command]]
        [thy-dungeonman.handler :only [message move]]))

(def commands
  (merge (make-command :look-flask -> "look" "flask")
         (make-command :go-north -> "go" "north")))

(def handlers
  {:look-flask (fn [game unknowns]
                 (message game "Looks like you could quaff some serious mead out of that thing."))
   :go-north (fn [game unknowns]
               (move game :north))})

(defn make-main
  "creates the initial main room"
  []
  (->Area :main commands handlers))
