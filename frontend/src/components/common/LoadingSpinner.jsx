import React from 'react';
import { Leaf } from 'lucide-react';

export default function LoadingSpinner({ message = "Loading Ayurvedic wisdom..." }) {
  return (
    <div className="flex flex-col items-center justify-center py-20 space-y-4">
      <div className="relative flex items-center justify-center">
        <div className="w-14 h-14 rounded-full border-4 border-ayur-100 border-t-ayur-600 animate-spin" />
        <Leaf className="w-6 h-6 text-ayur-600 absolute animate-pulse" />
      </div>
      <p className="text-sm font-medium text-gray-500 font-serif tracking-wide">{message}</p>
    </div>
  );
}
