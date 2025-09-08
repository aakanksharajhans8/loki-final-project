export default function PolicyPage() {
  return (
    <div className="min-h-screen bg-gray-50 pt-24">
      
      <section className="bg-gradient-to-r from-purple-600 to-indigo-600 text-white py-20">
        <div className="max-w-7xl mx-auto px-6 lg:px-12 flex flex-col lg:flex-row items-center gap-10">
        
          <div className="flex-1 flex flex-col justify-center">
            <h1 className="text-3xl sm:text-4xl lg:text-5xl font-extrabold leading-tight mb-4">
              Insurance — Protect What Matters Most
            </h1>
            <p className="text-sm sm:text-base text-purple-100 mb-6 max-w-xl">
              From health to life to your motor vehicles, our insurance plans are
              tailored to give you peace of mind and secure your future.
            </p>

            <div className="flex flex-wrap gap-3">
              <button className="px-5 py-3 bg-white text-purple-700 font-semibold rounded-lg shadow hover:bg-gray-100 transition">
                Get Started
              </button>
            </div>
          </div>

          
          <div className="flex-1 flex items-center justify-center">
            <div className="w-64 sm:w-72 md:w-80 lg:w-96 max-h-[420px]">
              <img
                src="https://cdn-icons-png.flaticon.com/512/942/942748.png"
                alt="Insurance illustration"
                className="w-full h-full object-contain drop-shadow-xl"
              />
            </div>
          </div>
        </div>
      </section>

      
      <section className="py-16 container mx-auto px-6 lg:px-20">
        <h2 className="text-3xl font-bold text-center mb-12">
          Our Insurance Policies
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          
          <div className="bg-white p-8 rounded-2xl shadow hover:shadow-lg transition border-t-4 border-green-500">
            <div className="flex justify-center mb-4">
              <div className="bg-green-100 p-4 rounded-full">
                <img
                  src="https://cdn-icons-png.flaticon.com/512/633/633652.png"
                  alt="Health Insurance"
                  className="w-12 h-12"
                />
              </div>
            </div>
            <h3 className="text-xl font-semibold text-center mb-2 text-green-700">
              Health Insurance
            </h3>
            <p className="text-gray-600 text-center">
              Comprehensive coverage for your health and medical expenses.
            </p>
          </div>

          
          <div className="bg-white p-8 rounded-2xl shadow hover:shadow-lg transition border-t-4 border-blue-500">
            <div className="flex justify-center mb-4">
              <div className="bg-blue-100 p-4 rounded-full">
                <img
                  src="https://cdn-icons-png.flaticon.com/512/743/743131.png"
                  alt="Motor Insurance"
                  className="w-12 h-12"
                />
              </div>
            </div>
            <h3 className="text-xl font-semibold text-center mb-2 text-blue-700">
              Motor Insurance
            </h3>
            <p className="text-gray-600 text-center">
              Protect your car, bike, or commercial vehicle against accidents and damages.
            </p>
          </div>

         
          <div className="bg-white p-8 rounded-2xl shadow hover:shadow-lg transition border-t-4 border-purple-500">
            <div className="flex justify-center mb-4">
              <div className="bg-purple-100 p-4 rounded-full">
                <img
                  src="https://cdn-icons-png.flaticon.com/512/942/942751.png"
                  alt="Life Insurance"
                  className="w-12 h-12"
                />
              </div>
            </div>
            <h3 className="text-xl font-semibold text-center mb-2 text-purple-700">
              Life Insurance
            </h3>
            <p className="text-gray-600 text-center">
              Secure your family’s future with our reliable life insurance plans.
            </p>
          </div>
        </div>
      </section>
    </div>
  );
}
