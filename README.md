# contacts-api-clojure

Example contacts REST API for demo in Clojure

## Libraries

1. [Http-Kit](https://www.http-kit.org)
2. [Compojure](https://github.com/weavejester/compojure)

## Usage

    $ lein run

## Routes

    GET /       => list of contacts
    GET /:id    => single contact
    POST /      => add new contact
    DELETE /:id => remove existing contact

