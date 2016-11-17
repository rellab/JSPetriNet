#!/usr/bin/ruby

class Vec
	def initialize(name, size, vecsize)
		@name = name
		@size = size
		@vecsize = vecsize
		@i = Array.new(size)
		@v = Array.new(size)
		@k = 0
	end

	def name
		@name
	end

	def add(i, v)
		@i[@k] = i
		@v[@k] = v
		@k += 1
	end

	def printVec
		print "#{@name} <- array(0, #{@size})\n"
		print "i <- c("
		print @i.join(",")
		print ")\n"
		print "x <- c("
		print @v.join(",")
		print ")\n"
		print "#{@name}[i] <- x\n"
	end

	def printVec2(f)
		@i.each_with_index do |x,i|
			f.print "#{@v[i].join(" ")}\n"
		end
	end
end

class Mat
	def initialize(name, x, y, nnz)
		@nnz = nnz
		@name = name
		@x = x
		@y = y
		@nnz = nnz
		@i = Array.new(nnz)
		@j = Array.new(nnz)
		@v = Array.new(nnz)
		@k = 0
	end

	def name
		@name
	end

	def row
		@x
	end

	def col
		@y
	end

	def add(i, j, v)
		@i[@k] = i
		@j[@k] = j
		@v[@k] = v
		@k += 1
	end

	def printMat2(f)
#		print "\# output elements #{@name} #{@v.length}\n"
		[@i,@j,@v].transpose.each do |x|
			f.print "#{x[0]} #{x[1]} #{x[2]}\n"
		end
	end
end

class Vecs

	def initialize(name, mat, col)
		@name = name
		@res = []
		@col = col
		@mat = mat
	end

	def read(suffix)
		@suffix = suffix
		f = open(@name + "." + suffix)
		while line = f.gets do
			if line =~ /^#/
				a = line.split(/\s+/)
				name = a[1]
				line = f.gets
				a = line.split(/\s+/)
				size = a[2].to_i
				vecsize = a[3].to_i
				@vecsize = vecsize
				print "\# create vector #{@name + name} #{size} #{vecsize}\n"
				m = Vec.new(name, size, vecsize)
				@res << m
			else
				a = line.split(/\s+/)
				m.add(a[1], a[2..(2+vecsize-1)])
			end
		end
		f.close
	end

	def writeVecFiles
		@res.each do |x|
			f=open(@name + x.name + ".dat", "w")
			x.printVec2(f)
			f.close
		end
	end

	def writeR
		@res.each do |x|
			print "dat <- scan(file=\"#{@name + x.name + ".dat"}\")\n"
			if @mat == true
				if @col == true
					print "#{@name + x.name} <- matrix(dat, ncol=#{@vecsize}, byrow=TRUE)\n"
				else
					print "#{@name + x.name} <- matrix(dat, nrow=#{@vecsize}, byrow=TRUE)\n"
				end
			else
				print "#{@name + x.name} <- dat\n"
			end
		end
	end
end

class Mats

	def initialize(name)
		@name = name
		@res = []
	end

	def read
		f = open(@name + ".matrix")
		while line = f.gets do
			if line =~ /^#/
				a = line.split(/\s+/)
				name = a[1]
				line = f.gets
				a = line.split(/\s+/)
				x = a[2].to_i
				y = a[3].to_i
				nnz = a[4].to_i
				print "\# create matrix #{@name + name} #{x} #{y} #{nnz}\n"
				m = Mat.new(name, x, y, nnz)
				@res << m
			else
				a = line.split(/\s+/)
				m.add(a[1], a[2], a[3])
			end
		end
		f.close
	end

	def writeMatFiles
		@res.each do |x|
			f=open(@name + x.name + ".dat", "w")
			x.printMat2(f)
			f.close
		end
	end

	def writeR
		@res.each do |x|
			print "dat <- scan(file=\"#{@name + x.name + ".dat"}\", list(i=0, j=0, x=0))\n"
			print "#{@name + x.name} <- sparseMatrix(i=dat$i, j=dat$j, x=dat$x, dims=c(#{x.row},#{x.col}))\n"
		end
	end
end

name = ARGV.shift
print "\# Create R file for #{name}\n"
m = Mats.new(name)
m.read
m.writeMatFiles
m.writeR

v = Vecs.new(name, false, false)
v.read("init")
v.writeVecFiles
v.writeR

v = Vecs.new(name, false, false)
v.read("sum")
v.writeVecFiles
v.writeR

if File.exist?(name + ".reward")
	v = Vecs.new(name, true, true)
	v.read("reward")
	v.writeVecFiles
	v.writeR
end
