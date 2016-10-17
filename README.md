# Style Effects Handlers for re-frame

> Style comes in all shapes and sizes. Therefore, the bigger you are, the more style you have. - Miss Piggy, The Muppets

Herein re-frame ["Effects Handlers"](https://github.com/Day8/re-frame/wiki/Effectful-Event-Handlers),
for performing styling tasks.

## Quick Start

### 1. Add Dependency

[![Clojars Project](https://img.shields.io/clojars/v/com.smxemail/re-frame-style-fx.svg)](https://clojars.org/com.smxemail/re-frame-style-fx)

### 2. Registration & Use

In the namespace where you register your event handlers, prehaps called
`handlers.cljs`, you have two things to do:

First, add this `require` to the `ns`:
```clj
(ns app.handlers
  (:require
    ...
    [com.smxemail.re-frame-style-fx]    ;; <-- add this
    ...))
```

Second, write an event handler which uses this effect:
```clj
(reg-event-fx
  :handler-with-style
  (fn [{:keys [db]} _]
    {:style/install {:content "body { font-family: Helvetica; }"
                     :on-success [:style-success]
                     :on-failure [:style-failure]}}))
```

Under the hood this uses [goog.style/installStyleSheet](http://google.github.io/closure-library/api/goog.style.html).

## Authors

- [Isaac Johnston](@superstructor)
- [Abhishek Reddy](@arbscht)

## License

Copyright &copy; 2016 SMX Ltd.

Distributed under the Eclipse Public License, the same as Clojure.
