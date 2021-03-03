---@class ModLogger
ModLogger = {}

---@type string
local name

---@type ZLogger
local logger

---@param loggerName string
function ModLogger:initialize(loggerName)

	name = loggerName
	logger = ZLogger.new(name, true)
	if ModLogger.logger == nil then
		print('ERROR: Unable to create ZLogger instance (' .. name .. ')')
	else
		ModLogger:debug('Initialized logger for name ' .. name)
	end
end

local function write(msg, level)
	logger:write(name .. ': ' .. msg, level)
end

---@param msg string
function ModLogger:error(msg)
	write(msg, "ERROR")
end

---@param msg string
function ModLogger:warn(msg)
	write(msg, "WARN")
end

---@param msg string
function ModLogger:info(msg)
	write(msg, "INFO")
end

---@param msg string
function ModLogger:debug(msg)
	write(msg, "DEBUG")
end

Events.OnInitWorld.Add(function()
	ModLogger:initialize("EHE")
end)