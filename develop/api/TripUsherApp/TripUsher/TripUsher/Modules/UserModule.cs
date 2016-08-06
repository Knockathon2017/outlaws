using BusinessLayer;
using Models;
using Nancy;
using System.Collections.Generic;
using System.Linq;
using TripUsher.ViewModels;
using Utilities;

namespace TripUsher.Modules
{
    public class UserModule : BaseModule
    {
        public UserModule()
        {
            UserService userService = new UserService();

            Post["/Login"] = _ =>
            {
                string userName = Request.Form.UserName;                
                string password = Request.Form.Password;
                bool isGuide = Request.Form.IsGuide;
                long mobileNumber = 0;
                bool isValidMobileNumber = long.TryParse(userName, out mobileNumber);

                var user = userService.LoginUser(isGuide, mobileNumber, password);
                var resposeCode = user == null ? Enums.ResponseCode.Unauthorized : Enums.ResponseCode.Success;
                return ConvertToJsonAndAddRCodeToHeader(new UserViewModel { IsValidUser = user != null, UserName = user.Name }, resposeCode);
            };

            Post["/SignUp"] = _ =>
            {
                User newUser = new User();
                long mobileNumber = 0;

                string mobileString = Request.Form.MobileNumber;
                bool isValidMobileNumber = long.TryParse(mobileString, out mobileNumber);
                newUser.Password = Request.Form.Password;
                newUser.Name = Request.Form.Name;
                newUser.LicenceNo = Request.Form.LicenceNo;
                newUser.IsGuide = Request.Form.IsGuide;
                newUser.IsOnline = true;
                newUser.IsAvailable = false;

                if (!isValidMobileNumber || string.IsNullOrEmpty(newUser.Password))
                    return ConvertToJsonAndAddRCodeToHeader(new ResponseViewModel { Message = "Invalid data" }, Enums.ResponseCode.Failure);
                else
                {
                    newUser.MobileNumber = mobileNumber;
                    User user = userService.AddUser(newUser);

                    if (user == null)
                        return ConvertToJsonAndAddRCodeToHeader(new ResponseViewModel { Message = "Invalid data" }, Enums.ResponseCode.Failure);
                    else
                        return ConvertToJsonAndAddRCodeToHeader(new UserViewModel { IsValidUser = true, UserName = user.Name }, Enums.ResponseCode.Success);
                }
            };

            Post["/GetGuides"] = _ =>
            {                
                string languagesString = Request.Form.Languages;
                string location = Request.Form.Location;
                int rating = Request.Form.Rating;
                List<string> languages = new List<string>();
                
                if(languagesString != null)
                    languages = languagesString.Split(',').ToList();

                var guides = userService.GetGuidesOnPreferences(languages, location, rating);

                return ConvertToJsonAndAddRCodeToHeader(new GuidesDetailViewModel { GuidesDetail = guides }, Enums.ResponseCode.Success); ;
            };

            Post["/GetGuideDetail"] = _ =>
            {
                string guideId = Request.Form.GuideId;
                var guideDetail = userService.GetGuideDetail(guideId);

                if (guideDetail == null)
                    return ConvertToJsonAndAddRCodeToHeader(new ResponseViewModel { Message = "Invalid guide id" }, Enums.ResponseCode.Failure);
                else
                    return ConvertToJsonAndAddRCodeToHeader(guideDetail, Enums.ResponseCode.Success);
            };

            Post["/ChangeGuideAvailability"] = _ =>
            {
                long mobileNumber = Request.Form.MobileNumber;
                bool isAvailable = Request.Form.IsAvailable;

                bool isAvailabilityChanged = userService.ChangeGuideAvailability(mobileNumber, isAvailable);

                if(isAvailabilityChanged)
                    return ConvertToJsonAndAddRCodeToHeader(new ResponseViewModel { Message = "Success" }, Enums.ResponseCode.Success);
                else
                    return ConvertToJsonAndAddRCodeToHeader(new ResponseViewModel { Message = "Invalid guide id" }, Enums.ResponseCode.Failure);
            };
        }
    }
}