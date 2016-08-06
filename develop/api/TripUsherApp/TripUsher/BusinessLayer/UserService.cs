using DatabaseLayer;
using Models;
using System.Collections.Generic;

namespace BusinessLayer
{
    public class UserService
    {
        UserRepository userRepository;

        public UserService()
        {
            userRepository = new UserRepository();
        }

        public User LoginUser(bool isGuide, long mobileNumber, string password)
        {
            return userRepository.LoginUser(isGuide, password, mobileNumber);
        }

        public User AddUser(User newUser)
        {
            return userRepository.AddUser(newUser);
        }

        public List<GuideDetailRating> GetGuidesOnPreferences(List<string> languages, string location, int rating)
        {
            return userRepository.GetGuidesOnPreferences(languages, location, rating);
        }

        public GuideDetailRating GetGuideDetail(string guideId)
        {
            return userRepository.GetGuideDetail(guideId);
        }
        
        public bool ChangeGuideAvailability(long mobileNumber, bool isAvailable)
        {
            return userRepository.ChangeGuideAvailability(mobileNumber, isAvailable);
        }
    }
}
