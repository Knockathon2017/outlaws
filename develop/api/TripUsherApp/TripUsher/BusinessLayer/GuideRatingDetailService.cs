using DatabaseLayer;
using Models;
using MongoDB.Bson;

namespace BusinessLayer
{
    public class GuideRatingDetailService
    {
        GuideRatingDetailRepository guideRatingDetailRepository;

        public GuideRatingDetailService()
        {
            guideRatingDetailRepository = new GuideRatingDetailRepository();
        }

        public void AddRating(string guideId, string touristId, int rating, string comment)
        {
            GuideRatingDetail guideRatingDetail = new GuideRatingDetail
            {
                TouristId = new ObjectId(touristId),
                GuideId = new ObjectId(guideId),
                Rating = rating,
                Comment = comment
            };
            guideRatingDetailRepository.AddRating(guideRatingDetail);
        }
    }
}
