using Models;
using MongoDB.Driver;
using System.Linq;
using Utilities;

namespace DatabaseLayer
{
    public class GuideRatingDetailRepository
    {
        IMongoDatabase db;

        public GuideRatingDetailRepository()
        {            
            var mongoClient = new MongoClient(Constants.DbConnectionString);
            db = mongoClient.GetDatabase(Constants.DatabaseName);
        }

        public void AddRating(GuideRatingDetail guideRatingDetail)
        {
            var guideRatingDetailCollection = db.GetCollection<GuideRatingDetail>(Constants.TableGuidesRatingDetail);
            var guideRatingCollection = db.GetCollection<GuideRating>(Constants.TableGuideRating);
            var guideRating = guideRatingCollection.AsQueryable().First(r => r.UserId == guideRatingDetail.GuideId);
            long ratingScore;
            int finalGuideRating;

            var isRequiredRatingExist = guideRatingDetailCollection.AsQueryable().Any(r => r.GuideId == guideRatingDetail.GuideId && r.TouristId == guideRatingDetail.TouristId);

            if (!isRequiredRatingExist)
            {
                guideRatingDetailCollection.InsertOne(guideRatingDetail);
                
                ratingScore = (guideRating.Rating * guideRating.CountRatings) + guideRatingDetail.Rating;
                finalGuideRating = (int)(ratingScore / (guideRating.CountRatings + 1));

                var filter = Builders<GuideRating>.Filter.Eq(s => s.Id, guideRating.Id);
                var update = Builders<GuideRating>.Update.Set(s => s.Rating, finalGuideRating).Set(s => s.CountRatings, guideRating.CountRatings + 1);
                guideRatingCollection.UpdateOne(filter, update);
            }
            else
            {
                ratingScore = (guideRating.Rating * guideRating.CountRatings) - guideRating.Rating + guideRatingDetail.Rating;
                finalGuideRating = (int)(ratingScore / (guideRating.CountRatings));

                var filter = Builders<GuideRating>.Filter.Eq(s => s.Id, guideRating.Id);
                var update = Builders<GuideRating>.Update.Set(s => s.Rating, finalGuideRating);
                guideRatingCollection.UpdateOne(filter, update);

                var filterBuilder = Builders<GuideRatingDetail>.Filter;
                var filterGuideRatingDetail = filterBuilder.Eq(r => r.GuideId, guideRatingDetail.GuideId) & filterBuilder.Eq(r => r.TouristId, guideRatingDetail.TouristId);
                var updateGuideRating = Builders<GuideRatingDetail>.Update.Set(s => s.Rating, guideRatingDetail.Rating).Set(s => s.Comment, guideRatingDetail.Comment);
                guideRatingDetailCollection.UpdateOne(filterGuideRatingDetail, updateGuideRating);
            }
        }
    }
}
