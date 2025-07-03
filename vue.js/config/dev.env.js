'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // API_ROOT: '"//192.168.1.14:9999/"'
   API_ROOT: '"//localhost:9999/"'
})
