package com.gymmanagement.DAO;

import com.gymmanagement.GymApplication;
import com.gymmanagement.Interface.IRepository;
import com.gymmanagement.beans.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * This class contains methods to perform CRUD operations on Gym management System.
 * @author Ajay.Srivas
 * @author Keerthana.Pai
 * @author Ashwini.D
 * @since 19/09/2017
 */
@RestController
@RequestMapping(value = "/member")
public class Controller {

    /**
     * This creates a reference for IRepository interface.
     * This reference is used to call the inbuilt methods of MongoRepository.
     */
    @Autowired
    IRepository db;
    /**
     * The application object used to access logger object.
     */
    GymApplication gymnasium;

    /**
     * This enrolls a new member into the Gym with all the needed details.
     * @param member represents a member who wants to enroll into Gym
     * @return  returns "member already exists" if the member is already present
     *          else returns "member with specified id is created successfully"
     */
    @RequestMapping(value = "/gymMember", method = RequestMethod.POST)
    public String createGymMember(@Validated @RequestBody Member member) {
        if(db.exists(member.getId())) {
            gymnasium.llog.error("Member already exists! :/");
            return "Member already exists! :/";
        }

        db.save(member);
        gymnasium.llog.debug("Member with " + member.getId() + " created successfully :)");
        return "Member with " + member.getId() + " created successfully :)";
    }

    /**
     * This will find the member with the specified id in the Gym.
     * @param id id of the member
     * @return returns the member with the specified id
     */
    @RequestMapping(value = "/gymMemberById/{id}", method = RequestMethod.GET)
    public Member getGymMemberById(@PathVariable("id") int id){
        Member newMember = db.findOne(id);
        if(newMember != null){
            gymnasium.llog.debug("Member with " + id + " exists!");
            return newMember;
        }
        gymnasium.llog.error("Member with " + id + " doesnt exists");
        return newMember;
    }


    /**
     * This will give the list of all the members in the Gym.
     * @return returns the list of members
     */
    @RequestMapping(value = "/allGymMembers", method = RequestMethod.GET)
    public List<Member> getAllGymMember(){
        List<Member> list = db.findAll();
        if(list == null)
            gymnasium.llog.error("No Members Found!");
        else
            gymnasium.llog.debug("Members Found");
        return list;
    }


    /**
     * This will update the info of the member if already exists or else creates a new member.
     * @param member member of the Gym whose info needs to be updated
     * @return message related to update operation
     */
    @RequestMapping(value = "/gymMember", method = RequestMethod.PUT)
    public String updateAGymMember(@Validated @RequestBody Member member){
        if(db.exists(member.getId())){
            db.save(member);
            gymnasium.llog.debug("Member Updated!");
            return "Member Updated!";
        }
        gymnasium.llog.error("Member does not exists!");
        return "Member does not exists!";
    }

    /**
     * This will delete the member with the specified id in the Gym.
     * @param id id of the member
     * @return message related to deleteGymMemberById operation
     */
    @RequestMapping(value = "/gymMemberById/{id}", method = RequestMethod.DELETE)
    public String deleteGymMemberById(@PathVariable("id") int id){
        db.delete(id);
        gymnasium.llog.debug("Member with " + id + " deleted");
        return "Member with " + id + " deleted";
    }

    /**
     * This will delete all the members of the Gym.
     * @return message related to deleteAllGymMembers operation
     */
    @RequestMapping(value = "/allGymMembers", method = RequestMethod.DELETE)
    public String deleteAllGymMembers(){
        db.deleteAll();
        gymnasium.llog.debug("Database Empty!");
        return "Database Empty!";
    }
}
