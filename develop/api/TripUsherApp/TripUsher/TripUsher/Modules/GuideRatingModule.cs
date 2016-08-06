using BusinessLayer;
using Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TripUsher.ViewModels;
using Utilities;

namespace TripUsher.Modules
{
    public class GuideRatingModule : BaseModule
    {
        public GuideRatingModule()
        {
            GuideRatingDetailService guideRatingDetailService = new GuideRatingDetailService();

            Post["/AddRating"] = _ =>
            {                
                string guideId = Request.Form.GuideId;
                string touristId = Request.Form.TouristId;
                int rating = Request.Form.Rating;
                string comment = Request.Form.Comment;

                if(guideId == null || touristId == null || rating <= 0)
                    return ConvertToJsonAndAddRCodeToHeader(new ResponseViewModel { Message = "Invalid data" }, Enums.ResponseCode.Failure);  

                guideRatingDetailService.AddRating(guideId, touristId, rating, comment);
                return ConvertToJsonAndAddRCodeToHeader(new ResponseViewModel { Message = "Success" }, Enums.ResponseCode.Success);                
            };
        }
    }
}