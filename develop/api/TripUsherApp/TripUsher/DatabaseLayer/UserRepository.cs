using Models;
using MongoDB.Bson;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Linq;
using Utilities;

namespace DatabaseLayer
{
    public class UserRepository
    {
        IMongoDatabase db;

        public UserRepository()
        {
            //var mongoClient = new MongoClient(ConfigurationManager.ConnectionStrings["MongoDB"].ConnectionString);
            //db = mongoClient.GetDatabase(ConfigurationManager.AppSettings["DatabaseName"]);
            var mongoClient = new MongoClient(Constants.DbConnectionString);
            db = mongoClient.GetDatabase(Constants.DatabaseName);
        }

        public User LoginUser(bool isGuide, string password, long mobileNumber)
        {
            var userData = db.GetCollection<User>(Constants.TableUsers);
            var user = userData.AsQueryable().FirstOrDefault(u => u.MobileNumber == mobileNumber && u.Password == password);

            if (user == null)
                return null;
            else
            {
                var filter = Builders<User>.Filter.Eq(s => s.Id, user.Id);
                var update = Builders<User>.Update.Set(s => s.IsOnline, true);
                var result = userData.UpdateOne(filter, update);
                var requiredUser = new User();
                
                if (isGuide)
                {
                    var guideDetail = db.GetCollection<GuideDetail>(Constants.TableGuideDetail).AsQueryable().First(d => d.LicenceNo == user.LicenceNo);
                    requiredUser.Name = guideDetail.Name;
                    requiredUser.Id = guideDetail.Id;
                }                    
                else
                {
                    requiredUser.Name = user.Name;
                    requiredUser.Id = user.Id;                  
                }
                return requiredUser;
            } 
        }

        public User AddUser(User newUser)
        {
            var userData = db.GetCollection<User>(Constants.TableUsers);

            if(newUser.IsGuide)
            {
                var isGuideRegistered = userData.AsQueryable().Any(u => u.LicenceNo == newUser.LicenceNo);

                if (isGuideRegistered)
                    return null;
                else
                {
                    var guideDetail = db.GetCollection<GuideDetail>(Constants.TableGuideDetail).AsQueryable().FirstOrDefault(d => d.LicenceNo == newUser.LicenceNo);

                    if (guideDetail != null)
                    {
                        newUser.IsAvailable = true;
                        userData.InsertOne(newUser);
                        db.GetCollection<GuideRating>(Constants.TableGuideRating).InsertOne(new GuideRating { UserId = guideDetail.Id, Rating = 0, CountRatings = 0 });
                        return new User { Id = guideDetail.Id, Name = guideDetail.Name };
                    }
                    else
                        return null;
                }
            }
            else
            {
                var isTouristRegistered = userData.AsQueryable().Any(u => u.MobileNumber == newUser.MobileNumber);

                if (isTouristRegistered)
                    return null;
                else
                {
                    userData.InsertOne(newUser);
                    return new User { Id = newUser.Id, Name = newUser.Name };
                }
            }
        }

        public List<GuideDetailRating> GetGuidesOnPreferences(List<string> languages, string location, int rating)
        {
            List<GuideDetailRating> requiredGuides = new List<GuideDetailRating>();
            var users = db.GetCollection<User>(Constants.TableUsers);
            var guideDetails = db.GetCollection<GuideDetail>(Constants.TableGuideDetail);
            var guideRating = db.GetCollection<GuideRating>(Constants.TableGuideRating);
            var availableGuides = users.AsQueryable().Where(u => u.IsGuide && u.IsOnline && u.IsAvailable);
            var guides = availableGuides.Join(guideDetails.AsQueryable(), u => u.LicenceNo, g => g.LicenceNo, (u, g) => g);
            var guidesInLocation = guides.Where(d => d.CityOperational.ToLower().Contains(location.ToLower())).ToList();
            var validGuides = guidesInLocation.Where(d => languages.Any(l => d.Languages.ToLower().Contains(l.ToLower())));
            var sortedGuidesList = validGuides.Join(guideRating.AsQueryable(), g => g.Id, r => r.UserId, (g, r) => new { g, r })
                                    .Where(cond => cond.r.Rating >= rating).OrderByDescending(rate => rate.r.Rating)
                                    .Take(Constants.GuidesPreferenceCount);

            requiredGuides = sortedGuidesList.Select(x => new GuideDetailRating
                                                            {
                                                                UserId = x.g.Id.ToString(),
                                                                Address = x.g.Address,
                                                                DateOfBirth = x.g.DateOfBirth,
                                                                Mobile = x.g.Mobile,
                                                                Name = x.g.Name,
                                                                Gender = x.g.Gender,
                                                                Languages = x.g.Languages,
                                                                Rating = x.r.Rating,
                                                                RatingCount = x.r.CountRatings
                                                            }).ToList();           
            return requiredGuides;
        }

        public GuideDetailRating GetGuideDetail(string guideId)
        {
            var guideObjectId = new ObjectId(guideId);
            var guideDetailsCollection = db.GetCollection<GuideDetail>(Constants.TableGuideDetail);
            var guideDetail = guideDetailsCollection.AsQueryable().FirstOrDefault(g => g.Id == guideObjectId);

            if (guideDetail != null)
            {
                var guideRating = db.GetCollection<GuideRating>(Constants.TableGuideRating).AsQueryable()
                                                .First(g => g.Id == guideObjectId);
                return new GuideDetailRating
                {
                    UserId = guideDetail.Id.ToString(),
                    Address = guideDetail.Address,
                    DateOfBirth = guideDetail.DateOfBirth,
                    Mobile = guideDetail.Mobile,
                    Name = guideDetail.Name,
                    Gender = guideDetail.Gender,
                    Languages = guideDetail.Languages,
                    Rating = guideRating.Rating,
                    RatingCount = guideRating.CountRatings
                };
            }
            else
                return null;
        }

        public bool ChangeGuideAvailability(long mobileNumber, bool isAvailable)
        {            
            var user = db.GetCollection<User>(Constants.TableUsers).AsQueryable().FirstOrDefault(g => g.MobileNumber == mobileNumber && g.IsGuide);

            if (user == null)
                return false;
            else
            {
                var filterBuilder = Builders<User>.Filter;
                var filter = filterBuilder.Eq(s => s.MobileNumber, mobileNumber) & filterBuilder.Eq(s => s.IsGuide, true);
                var update = Builders<User>.Update.Set(s => s.IsAvailable, isAvailable);
                db.GetCollection<User>(Constants.TableUsers).UpdateOne(filter, update);
                return true;
            }
        }
    }
}
