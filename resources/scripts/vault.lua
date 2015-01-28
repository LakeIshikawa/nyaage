--
-- Created by IntelliJ IDEA.
-- User: lake
-- Date: 14/12/25
-- Time: 15:12
--
-- Bank's vault

--Utils
function updateVaultDoor()
    -- Initialize closed
    if vaultDoorOpened == Nil then
        vaultDoorOpened = false
    end

    -- Update
    if vaultDoorOpened then
        oVaultDoorOpened:setVisible(true)
        oVaultDoorClosed:setVisible(false)
    else
        oVaultDoorOpened:setVisible(false)
        oVaultDoorClosed:setVisible(true)
    end
end


-- EVENTS

function hBananas_use()
    cKeymon:walkTo(300, 100)
    cKeymon:say("Some bananas!")
end

function afterLoad()
    cKeymon:faceDirection(Direction.LEFT)
    updateVaultDoor()
end

function afterFade()
    nya:beginCutscene()
    cKeymon:say("Boring...")
    nya:wait(1.0)
    cKeymon:say("...so boring...")
    nya:wait(1.0)

    vaultDoorOpened = true
    updateVaultDoor()
    cBoss:changeRoom(1, 787, 206);
    cBoss:say("Keymon!");
    cKeymon:faceCharacter(cBoss);
    cBoss:walkTo(730, 141);
    cBoss:faceCharacter(cKeymon);
    cBoss:say("Did you finish counting those banans already? We need them counted until tomorrow!");
    cBoss:say("I finally got a date with Amanda tonight, and I am NOT going to miss it because of YOU!");
    cBoss:say("Hurry up already!");
    cBoss:walkTo(787,  206);
    vaultDoorOpened = false
    updateVaultDoor()
    cBoss:changeRoom(2, 521, 46);

    nya:endCutscene()
end