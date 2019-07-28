# contacts-api-clojure

Example contacts REST API for demo in Clojure

## Libraries

1. [ring](https://github.com/ring-clojure/ring)
2. [reitit](https://github.com/metosin/reitit)

## Usage

    $ lein run

## Routes

    GET /       => list of contacts
    GET /:id    => single contact
    POST /      => add new contact
    DELETE /:id => remove existing contact

