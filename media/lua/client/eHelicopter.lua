---@class eHelicopter
---@field soundRef long
---@field emitter FMODSoundEmitter
eHelicopter = {}

---@param obj IsoMovingObject
---@return FMODSoundEmitter
local function getFreeEmitterForMovingObject(obj)
	return getWorld():getFreeEmitter(obj:getX() + 0.5, obj:getY() + 0.5, 20)
end

---@param target IsoMovingObject
---@return FMODSoundEmitter
function eHelicopter:createEmitter(target)

	local result = getFreeEmitterForMovingObject(target)
	ModLogger:debug("Created new helicopter emitter (" .. tostring(result) .. ')')
	eHelicopter.emitter = result
	return result
end

---@param target IsoMovingObject
---@return long @sound reference number
function eHelicopter:playSound(target)
	return eHelicopter:createEmitter(target):playSoundImpl("Helicopter", target)
end

---@return boolean
function eHelicopter:isSoundPlaying()
	return eHelicopter.emitter and eHelicopter.emitter:isPlaying("Helicopter") or false
end

function eHelicopter:launch()

	--the -1 is to offset playerIDs starting at 0
	local numActivePlayers = getNumActivePlayers() - 1

	---target is a random IsoPlayer
	---@type IsoMovingObject
	local target = getSpecificPlayer(ZombRand(numActivePlayers))
	ModLogger:debug("Set helicopter target to player " .. target:getObjectName())

	--start playing helicopter sound
	local ref = eHelicopter:playSound(target)
	ModLogger:debug("Playing helicopter noise (" .. tostring(ref) .. ')')
	eHelicopter.soundRef = ref
end

