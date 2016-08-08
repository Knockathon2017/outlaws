using Nancy;
using Utilities;

namespace TripUsher.Modules
{
    public class BaseModule : NancyModule
    {
        public BaseModule()
        {
        }

        protected BaseModule(string modulePath)
            : base(modulePath)
        {
        }

        protected Response ConvertToJsonAndAddRCodeToHeader(object response, Enums.ResponseCode responseCode)
        {
            int rCode = (int)responseCode;
            return Response.AsJson(response).WithHeader("Code", rCode.ToString());
        }
    }
}