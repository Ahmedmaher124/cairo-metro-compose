package com.ahmed.cairo_metro_compose.domain.usecase

import com.ahmed.cairo_metro_compose.domain.model.lines
import com.ahmed.cairo_metro_compose.domain.model.station
import java.util.LinkedList
import java.util.Queue

class BFSUseCase {
    operator fun invoke(start: station, end: station, stations: List<station>): List<station>? {
        if (start == end) return listOf(start)

        val queue: Queue<station> = LinkedList()
        val visited = mutableSetOf<station>()
        val parentMap = mutableMapOf<station, station?>()

        queue.add(start)
        visited.add(start)
        parentMap[start] = null

        val stationByLine = stations.groupBy { it.line }
        val stationByName = stations.groupBy { it.name }

        while (queue.isNotEmpty()) {
            val current = queue.poll() ?: continue
            if (current == end) {
                return buildPath(end, parentMap)
            }
            
            val neighbors = findNeighbours(current, stationByLine, stationByName)
            for (neighbor in neighbors) {
                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                    parentMap[neighbor] = current
                }
            }
        }
        return null
    }

    private fun findNeighbours(
        station: station,
        stationByLine: Map<lines, List<station>>,
        stationByName: Map<String, List<station>>
    ): List<station> {
        val sameLineNeighbors = stationByLine[station.line]
            ?.filter {
                it.order == station.order - 1 || it.order == station.order + 1
            } ?: emptyList()
            
        val transfer = if (station.is_transfer) {
            stationByName[station.name]?.filter { it.line != station.line } ?: emptyList()
        } else {
            emptyList()
        }
        return sameLineNeighbors + transfer
    }

    private fun buildPath(end: station, parentMap: Map<station, station?>): List<station> {
        val path = mutableListOf<station>()
        var current: station? = end
        while (current != null) {
            path.add(current)
            current = parentMap[current]
        }
        return path.reversed()
    }
}
