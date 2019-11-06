local lkey = KEYS[1]
local lvalue = KEYS[2]

local result = redis.call('SET',lkey,lvalue,'ex',20,'nx')
return result